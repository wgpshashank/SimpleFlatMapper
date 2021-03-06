package org.sfm.reflect.asm;

import org.objectweb.asm.*;
import org.sfm.reflect.ConstructorDefinition;
import org.sfm.reflect.ConstructorParameter;
import org.sfm.reflect.TypeHelper;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AsmConstructorDefinitionFactory {

    public static <T> List<ConstructorDefinition<T>> extractConstructors(final Type target) throws IOException {
        final List<ConstructorDefinition<T>> constructors = new ArrayList<ConstructorDefinition<T>>();

        final Class<T> targetClass = TypeHelper.toClass(target);

        ClassLoader cl = targetClass.getClassLoader();
        if (cl == null) {
            cl = ClassLoader.getSystemClassLoader();
        }

        final InputStream is = cl.getResourceAsStream(targetClass.getName().replace('.', '/') + ".class");
        try {
            ClassReader classReader = new ClassReader(is);
            classReader.accept(new ClassVisitor(Opcodes.ASM5) {
                List<String> genericTypeNames;

                @Override
                public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
                    if (signature != null) {
                        genericTypeNames =  AsmUtils.extractGenericTypeNames(signature);
                    } else {
                        genericTypeNames = Collections.emptyList();
                    }
                    super.visit(version, access, name, signature, superName, interfaces);
                }

                @Override
                public MethodVisitor visitMethod(int access,
                                                 String name,
                                                 String desc,
                                                 String signature,
                                                 String[] exceptions) {
                    if ("<init>".equals(name)) {
                        final List<String> descTypes = AsmUtils.extractConstructorTypeNames(desc);
                        final List<String> genericTypes;
                        final List<String> names = new ArrayList<String>();
                        if (signature != null) {
                            genericTypes = AsmUtils.extractConstructorTypeNames(signature);
                        } else {
                            genericTypes = descTypes;
                        }

                        return new MethodVisitor(Opcodes.ASM5) {


                            Label firstLabel;
                            Label lastLabel;
                            @Override
                            public void visitLabel(Label label) {
                                if (firstLabel == null) {
                                    firstLabel = label;
                                }
                                lastLabel = label;
                            }

                            @Override
                            public void visitLocalVariable(String name, String desc, String signature, Label start, Label end, int index) {
                                if (start.equals(firstLabel) && end.equals(lastLabel) && ! "this".equals(name)) {
                                    names.add(name);
                                }
                            }


                            @Override
                            public void visitEnd() {
                                try {
                                    final List<ConstructorParameter> parameters = new ArrayList<ConstructorParameter>();

                                    for(int i = 0; i < descTypes.size(); i ++) {
                                        String name = "arg" + i;
                                        if (i < names.size()) {
                                            name =names.get(i);
                                        }
                                        parameters.add(createParameter(name, descTypes.get(i), genericTypes.get(i)));
                                    }

                                    constructors.add(new ConstructorDefinition<T>(targetClass.getDeclaredConstructor(toTypeArray(parameters)), parameters.toArray(new ConstructorParameter[parameters.size()])));
                                } catch(Exception e) {
                                    throw new Error("Unexpected error " + e, e);
                                }
                            }

                            private Class<?>[] toTypeArray(List<ConstructorParameter> parameters) {
                                Class<?>[] types = new Class<?>[parameters.size()];
                                for(int i = 0; i < types.length; i++) {
                                    types[i] = TypeHelper.toClass(parameters.get(i).getType());
                                }
                                return types;
                            }

                            private ConstructorParameter createParameter(String name,
                                                                         String desc, String signature) {
                                try {

                                    Type basicType = AsmUtils.toGenericType(desc, genericTypeNames, target);
                                    Type genericType = basicType;
                                    if (signature != null) {
                                        genericType = AsmUtils.toGenericType(signature, genericTypeNames, target);
                                    }
                                    return new ConstructorParameter(name, basicType, genericType);
                                } catch (ClassNotFoundException e) {
                                    throw new Error("Unexpected error " + e, e);
                                }
                            }
                        };
                    } else {
                        return null;
                    }
                }



            }, 0);
        } finally {
            try { is.close(); } catch(Exception e) {}
        }

        return constructors;
    }
}
