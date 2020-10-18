package org.imzdong.study.msb.design.visitor.asm;

import org.objectweb.asm.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static org.objectweb.asm.Opcodes.ASM4;
import static org.objectweb.asm.Opcodes.INVOKESTATIC;

/**
 * asm读取，修改class
 * https://asm.ow2.io/
 */
public class AsmMain {

    public static void main(String[] args) throws Exception{
        //asmSimpleRead();
        //asmReadClass();
        asmWriteClass();
    }

    private static void asmWriteClass() throws IOException {
        String clazz = "org/imzdong/study/msb/design/visitor/asm/Winter.class";
        ClassReader cr = new ClassReader(AsmMain.class.getClassLoader().getResourceAsStream(clazz));
        ClassWriter cw = new ClassWriter(0);
        ClassVisitor cv = new ClassVisitor(ASM4, cw) {
            @Override
            public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
                MethodVisitor mv = super.visitMethod(access, name, descriptor, signature, exceptions);
                return new MethodVisitor(ASM4, mv) {
                    @Override
                    public void visitCode() {
                        //org.imzdong.study.msb.design.visitor.asm.WinterProxy
                        visitMethodInsn(INVOKESTATIC, "com/imzdong/study/msb/design/visitor/asm/WinterProxy","proxy", "()V", false);
                        super.visitCode();
                    }
                };
            }
        };
        cr.accept(cv, 0);
        byte[] b2 = cw.toByteArray();

        String path = (String)System.getProperties().get("user.dir");
        File f = new File(path + "/com/imzdong/study/asm/");
        f.mkdirs();

        FileOutputStream fos = new FileOutputStream(new File(path + "/com/imzdong/study/asm/Winter_0.class"));
        fos.write(b2);
        fos.flush();
        fos.close();
    }

    private static void asmReadClass() throws IOException {
        ClassReader classReader = new ClassReader("java.lang.Runnable");
        ClassWriter classWriter = new ClassWriter(0);
        classReader.accept(classWriter, 0);
        byte[] b2 = classWriter.toByteArray();
        String path = (String)System.getProperties().get("user.dir");

        FileOutputStream fos = new FileOutputStream(new File(path + "/R_0.class"));
        fos.write(b2);
        fos.flush();
        fos.close();
    }

    private static void asmSimpleRead() throws IOException {
        MyClassPrinter printer = new MyClassPrinter();
        //String clazz = "org/imzdong/study/msb/design/visitor/asm/Winter.class";
        //ClassReader cr = new ClassReader(AsmMain.class.getClassLoader().getResourceAsStream(clazz));
        ClassReader cr = new ClassReader("java.lang.Runnable");
        cr.accept(printer, 0);
    }

}
class MyClassPrinter extends ClassVisitor{
    public MyClassPrinter(){super(ASM4);}

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        System.out.println(name + " extends " + superName + "{" );
    }

    @Override
    public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
        System.out.println("    " + name);
        return null;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        System.out.println("    " + name + "()");
        return null;
    }

    @Override
    public void visitEnd() {

        System.out.println("}");
    }
}
