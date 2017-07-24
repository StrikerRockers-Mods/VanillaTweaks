package com.strikerrocker.vt.coremod;

import com.strikerrocker.vt.main.vtModInfo;
import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.*;

import static org.objectweb.asm.Opcodes.*;

/**
 * The class transformer for Craft++
 */
public class VTClassTransformer implements IClassTransformer {
    /**
     * The class name to delegate the methods to
     */
    private static final String DELEGATE_CLASS_NAME = vtModInfo.PACKAGE_LOCATION.replace('.', '/') + "/coremod/VTCoremodHooks";

    protected static boolean obfuscated;

    /**
     * Returns whether or not the given class name represents a vanilla block class
     *
     * @param className The class name
     * @return Whether or not the class name represents a vanilla block class
     */
    private static boolean isVanillaBlockClass(String className) {
        return className != null && (className.startsWith("net.minecraft.block.Block") || className.startsWith("net/minecraft/block/Block")) && !className.contains("$") && !className.endsWith("EventData");
    }

    @Override
    public byte[] transform(String className, String deobfuscatedClassName, byte[] bytes) {
        try {
            ClassNode classNode = new ClassNode();
            ClassReader classReader = new ClassReader(bytes);
            classReader.accept(classNode, 0);
            if (isVanillaBlockClass(deobfuscatedClassName) || isVanillaBlockClass(classNode.superName))
                return this.transformBlock(bytes, deobfuscatedClassName);
            return bytes;
        } catch (Exception e) {
            return bytes;
        }
    }

    /**
     * Modifies the block class to allow Craft++'s falling blocks to fall
     *
     * @param bytes The bytes of the block class
     * @return The modified bytes of the block class
     */
    private byte[] transformBlock(byte[] bytes, String deobfuscatedClassName) {
        ClassNode classNode = new ClassNode();
        ClassReader classReader = new ClassReader(bytes);
        classReader.accept(classNode, 0);

        String targetMethodName = obfuscated ? "c" : "onBlockAdded";
        String targetMethodName1 = obfuscated ? "a" : "neighborChanged";
        String targetMethodName2 = obfuscated ? "b" : "updateTick";
        String targetMethodName3 = obfuscated ? "a" : "tickRate";

        String targetMethodDescriptor = obfuscated ? "(Laid;Lcm;Lars;)V" : "(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/state/IBlockState;)V";
        String targetMethodDescriptor1 = obfuscated ? "(Lars;Laid;Lcm;Lakf;)V" : "(Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/Block;)V";
        String targetMethodDescriptor2 = obfuscated ? "(Laid;Lcm;Lars;Ljava/util/Random;)V" : "(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/state/IBlockState;Ljava/util/Random;)V";
        String targetMethodDescriptor3 = obfuscated ? "(Laid;)I" : "(Lnet/minecraft/world/World;)I";

        String delegateMethodDescriptor = obfuscated ? "(Laid;Lcm;Lakf;)V" : "(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/Block;)V";
        String delegateMethodDescriptor1 = obfuscated ? "(Lakf;)Z" : "(Lnet/minecraft/block/Block;)Z";
        int patchCount = 0;
        for (MethodNode methodNode : classNode.methods) {
            String methodName = methodNode.name;
            String methodDescriptor = methodNode.desc;
            InsnList methodInstructions = methodNode.instructions;
            if (methodName.equals(targetMethodName) && methodDescriptor.equals(targetMethodDescriptor)) {
                InsnList injectionList = new InsnList();
                injectionList.add(new VarInsnNode(ALOAD, 1));
                injectionList.add(new VarInsnNode(ALOAD, 2));
                injectionList.add(new VarInsnNode(ALOAD, 0));
                injectionList.add(new MethodInsnNode(INVOKESTATIC, DELEGATE_CLASS_NAME, "scheduleBlockUpdate", delegateMethodDescriptor, false));
                methodInstructions.insert(injectionList);
                ++patchCount;
            }
            if (methodName.equals(targetMethodName1) && methodDescriptor.equals(targetMethodDescriptor1)) {
                InsnList injectionList = new InsnList();
                injectionList.add(new VarInsnNode(ALOAD, 2));
                injectionList.add(new VarInsnNode(ALOAD, 3));
                injectionList.add(new VarInsnNode(ALOAD, 0));
                injectionList.add(new MethodInsnNode(INVOKESTATIC, DELEGATE_CLASS_NAME, "scheduleBlockUpdate", delegateMethodDescriptor, false));
                methodInstructions.insert(injectionList);
                ++patchCount;
            }
            if (methodName.equals(targetMethodName2) && methodDescriptor.equals(targetMethodDescriptor2)) {
                InsnList injectionList = new InsnList();
                injectionList.add(new VarInsnNode(ALOAD, 1));
                injectionList.add(new VarInsnNode(ALOAD, 2));
                injectionList.add(new VarInsnNode(ALOAD, 0));
                injectionList.add(new MethodInsnNode(INVOKESTATIC, DELEGATE_CLASS_NAME, "onUpdateTick", delegateMethodDescriptor, false));
                methodInstructions.insert(injectionList);
                ++patchCount;
            }
            if (methodName.equals(targetMethodName3) && methodDescriptor.equals(targetMethodDescriptor3)) {
                InsnList injectionList = new InsnList();
                injectionList.add(new VarInsnNode(ALOAD, 0));
                injectionList.add(new MethodInsnNode(INVOKESTATIC, DELEGATE_CLASS_NAME, "isFallingBlock", delegateMethodDescriptor1, false));
                LabelNode labelNode = new LabelNode();
                injectionList.add(new JumpInsnNode(IFEQ, labelNode));
                injectionList.add(new InsnNode(ICONST_2));
                injectionList.add(new InsnNode(IRETURN));
                injectionList.add(labelNode);
                methodInstructions.insert(injectionList);
                ++patchCount;
            }
        }
        if (deobfuscatedClassName.equals("net.minecraft.block.BlockCactus")) {
            String cactusTargetMethodName = obfuscated ? "b" : "canBlockStay";
            String cactusTargetMethodDescriptor = obfuscated ? "(Laid;Lcm;)Z" : "(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)Z";
            String cactusDelegateMethodDescriptor = obfuscated ? "(Laid;Lcm;Lakf;)Z" : "(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/Block;)Z";
            classNode.methods.stream().filter(methodNode -> methodNode.name.equals(cactusTargetMethodName) && methodNode.desc.equals(cactusTargetMethodDescriptor)).forEach(methodNode -> {
                InsnList injectionList = new InsnList();
                injectionList.add(new VarInsnNode(ALOAD, 1));
                injectionList.add(new VarInsnNode(ALOAD, 2));
                injectionList.add(new VarInsnNode(ALOAD, 0));
                injectionList.add(new MethodInsnNode(INVOKESTATIC, DELEGATE_CLASS_NAME, "canCactusStay", cactusDelegateMethodDescriptor, false));
                injectionList.add(new InsnNode(IRETURN));
                methodNode.instructions.insert(injectionList);
            });
            ++patchCount;
        }
        if (patchCount > 0)
            System.out.println("Patched " + patchCount + " method" + (patchCount > 1 ? "s" : "") + " in class " + deobfuscatedClassName);
        else
            return bytes;
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
        classNode.accept(classWriter);
        return classWriter.toByteArray();
    }
}
