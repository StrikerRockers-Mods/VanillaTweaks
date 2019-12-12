function initializeCoreMod() {
    return {
        'drops': {
            'target': {
                'type': 'METHOD',
                'class': 'net.minecraft.block.Block',
                'methodName': 'func_220076_a', // getDrops
                'methodDesc': '(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/storage/loot/LootContext$Builder;)Ljava/util/List;'
            },
            'transformer': function(method) {
              print('[UnstableTools]: Patching Minecraft\' Block#getDrops');

                var ASM = Java.type('net.minecraftforge.coremod.api.ASMAPI');
                var Opcodes = Java.type('org.objectweb.asm.Opcodes');
                var VarInsnNode = Java.type('org.objectweb.asm.tree.VarInsnNode');
                var InsnList = Java.type('org.objectweb.asm.tree.InsnList');
                var InsnNode = Java.type('org.objectweb.asm.tree.InsnNode');

                var instructions = method.instructions;
                var lastInstruction = instructions.get(42);

                var newInstructions = new InsnList();

                newInstructions.add(new VarInsnNode(Opcodes.ALOAD, 6));
                newInstructions.add(new VarInsnNode(Opcodes.ALOAD, 4));


                newInstructions.add(ASM.buildMethodCall(
                    "io/github/strikerrocker/vt/misc/ASMHooks",
                    "getdrops",
                    "(Lnet/minecraft/world/storage/loot/LootTable;Lnet/minecraft/world/storage/loot/LootContext;)Ljava/util/List;",
                    ASM.MethodType.STATIC
                ));

                newInstructions.add(new VarInsnNode(Opcodes.ASTORE, 7));
                newInstructions.add(new VarInsnNode(Opcodes.ALOAD, 7));

                method.instructions.insertBefore(lastInstruction, newInstructions);

                return method;
            }
        }
     }
  }