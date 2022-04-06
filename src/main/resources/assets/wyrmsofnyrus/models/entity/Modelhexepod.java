// Made with Blockbench 4.1.0
// Exported for Minecraft version 1.7 - 1.12
// Paste this class into your mod and generate all required imports

public static class Modelhexepod extends ModelBase {
	private final ModelRenderer Pod;

	public Modelhexepod() {
		textureWidth = 64;
		textureHeight = 64;

		Pod = new ModelRenderer(this);
		Pod.setRotationPoint(0.0F, 24.0F, 0.0F);
		Pod.cubeList.add(new ModelBox(Pod, 0, 17, -3.0F, -13.0F, -3.0F, 6, 13, 6, 0.0F, false));
		Pod.cubeList.add(new ModelBox(Pod, 0, 0, -4.0F, -10.0F, -4.0F, 8, 9, 8, 0.0F, false));
		Pod.cubeList.add(new ModelBox(Pod, 24, 0, -2.0F, -15.0F, -2.0F, 4, 4, 4, 0.0F, false));
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		Pod.render(f5);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}

	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity e) {
		super.setRotationAngles(f, f1, f2, f3, f4, f5, e);
	}
}