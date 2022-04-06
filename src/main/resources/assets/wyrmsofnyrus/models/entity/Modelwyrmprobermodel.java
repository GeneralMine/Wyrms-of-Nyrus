// Made with Blockbench
// Paste this code into your mod.
// Make sure to generate all required imports

public static class Modelwyrmprobermodel extends ModelBase {
	private final ModelRenderer Body;
	private final ModelRenderer wingLeft;
	private final ModelRenderer wingRight;
	private final ModelRenderer End;
	private final ModelRenderer Head;
	private final ModelRenderer Prob;

	public Modelwyrmprobermodel() {
		textureWidth = 64;
		textureHeight = 64;

		Body = new ModelRenderer(this);
		Body.setRotationPoint(-0.0833F, 19.4583F, 0.5F);
		Body.cubeList.add(new ModelBox(Body, 13, 23, -2.4167F, -5.2083F, -5.5F, 5, 4, 11, 0.0F, false));
		Body.cubeList.add(new ModelBox(Body, 0, 13, -3.4167F, -2.4583F, -3.5F, 6, 2, 8, 0.0F, false));

		wingLeft = new ModelRenderer(this);
		wingLeft.setRotationPoint(2.5833F, -3.9583F, 1.0F);
		Body.addChild(wingLeft);
		wingLeft.cubeList.add(new ModelBox(wingLeft, 8, 7, -1.5F, -0.5F, -2.5F, 9, 1, 5, 0.0F, false));

		wingRight = new ModelRenderer(this);
		wingRight.setRotationPoint(-2.4167F, -3.9583F, 1.0F);
		Body.addChild(wingRight);
		wingRight.cubeList.add(new ModelBox(wingRight, 8, 7, -7.5F, -0.5F, -2.5F, 9, 1, 5, 0.0F, false));

		End = new ModelRenderer(this);
		End.setRotationPoint(0.0833F, -2.4583F, 8.0F);
		Body.addChild(End);
		setRotationAngle(End, -0.2618F, 0.0F, 0.0F);
		End.cubeList.add(new ModelBox(End, 34, 13, -2.25F, -1.25F, -5.5F, 4, 3, 11, 0.0F, false));

		Head = new ModelRenderer(this);
		Head.setRotationPoint(0.0833F, 4.5417F, -3.5F);
		Body.addChild(Head);
		Head.cubeList.add(new ModelBox(Head, 38, 0, -3.0F, -10.0F, -8.0F, 6, 6, 7, 0.0F, false));

		Prob = new ModelRenderer(this);
		Prob.setRotationPoint(0.5F, -4.5F, -9.5F);
		Head.addChild(Prob);
		setRotationAngle(Prob, 1.0472F, 0.0F, 0.0F);
		Prob.cubeList.add(new ModelBox(Prob, 29, 6, -1.0F, -0.116F, -6.1651F, 1, 1, 7, 0.0F, false));
		Prob.cubeList.add(new ModelBox(Prob, 22, 0, -2.25F, -1.2679F, 0.134F, 3, 3, 4, 0.0F, false));
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		Body.render(f5);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}

	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity e) {
		super.setRotationAngles(f, f1, f2, f3, f4, f5, e);
		this.wingRight.rotateAngleZ = MathHelper.cos(f * 1.0F) * 1.0F * f1;
		this.wingLeft.rotateAngleZ = MathHelper.cos(f * 1.0F) * -1.0F * f1;
		this.Body.rotateAngleY = f3 / (180F / (float) Math.PI);
		this.Body.rotateAngleX = f4 / (180F / (float) Math.PI);
	}
}