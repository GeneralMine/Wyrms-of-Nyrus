// Made with Blockbench
// Paste this code into your mod.
// Make sure to generate all required imports

public static class ModelroyalgrubModel extends ModelBase {
	private final ModelRenderer Head;
	private final ModelRenderer Fangs;
	private final ModelRenderer Fang4;
	private final ModelRenderer Fang3;
	private final ModelRenderer Fang2;
	private final ModelRenderer Fang1;
	private final ModelRenderer Body;
	private final ModelRenderer BodyMid;
	private final ModelRenderer BodyEnd;

	public ModelroyalgrubModel() {
		textureWidth = 128;
		textureHeight = 80;

		Head = new ModelRenderer(this);
		Head.setRotationPoint(0.0F, 17.1111F, -19.9444F);
		Head.cubeList.add(new ModelBox(Head, 22, 43, -6.5F, -6.1111F, -9.0556F, 12, 12, 9, 0.0F, false));
		Head.cubeList.add(new ModelBox(Head, 0, 42, -5.5F, -5.1111F, -11.5556F, 2, 2, 3, 0.0F, false));
		Head.cubeList.add(new ModelBox(Head, 0, 42, 2.5F, -5.1111F, -11.5556F, 2, 2, 3, 0.0F, false));
		Head.cubeList.add(new ModelBox(Head, 0, 42, 2.5F, 2.8889F, -11.5556F, 2, 2, 3, 0.0F, false));
		Head.cubeList.add(new ModelBox(Head, 0, 42, -5.5F, 2.8889F, -11.5556F, 2, 2, 3, 0.0F, false));

		Fangs = new ModelRenderer(this);
		Fangs.setRotationPoint(-3.5F, 3.3889F, -11.8056F);
		Head.addChild(Fangs);

		Fang4 = new ModelRenderer(this);
		Fang4.setRotationPoint(0.0F, -7.0F, 0.0F);
		setRotationAngle(Fang4, -0.2618F, -0.5236F, 1.2217F);
		Fangs.addChild(Fang4);
		Fang4.cubeList.add(new ModelBox(Fang4, 0, 38, -0.75F, 0.0F, -1.75F, 1, 1, 3, 0.0F, false));

		Fang3 = new ModelRenderer(this);
		Fang3.setRotationPoint(7.0F, -7.0F, 0.0F);
		setRotationAngle(Fang3, -0.2618F, 0.5236F, -1.0472F);
		Fangs.addChild(Fang3);
		Fang3.cubeList.add(new ModelBox(Fang3, 0, 38, -0.75F, -1.0F, -1.75F, 1, 1, 3, 0.0F, false));

		Fang2 = new ModelRenderer(this);
		Fang2.setRotationPoint(7.0F, 0.0F, 0.0F);
		setRotationAngle(Fang2, -0.2618F, 0.5236F, 0.4363F);
		Fangs.addChild(Fang2);
		Fang2.cubeList.add(new ModelBox(Fang2, 0, 38, -1.25F, -0.25F, -1.75F, 1, 1, 3, 0.0F, false));

		Fang1 = new ModelRenderer(this);
		Fang1.setRotationPoint(0.0F, 0.0F, 0.0F);
		setRotationAngle(Fang1, -0.2618F, -0.5236F, -0.4363F);
		Fangs.addChild(Fang1);
		Fang1.cubeList.add(new ModelBox(Fang1, 0, 38, -0.75F, -0.75F, -1.75F, 1, 1, 3, 0.0F, false));

		Body = new ModelRenderer(this);
		Body.setRotationPoint(0.0F, 20.5F, -8.5F);
		Body.cubeList.add(new ModelBox(Body, 60, 0, -7.5F, -10.5F, -12.5F, 14, 14, 20, 0.0F, false));

		BodyMid = new ModelRenderer(this);
		BodyMid.setRotationPoint(-0.25F, -0.25F, 9.5F);
		Body.addChild(BodyMid);
		BodyMid.cubeList.add(new ModelBox(BodyMid, 48, 41, -8.25F, -11.25F, -3.0F, 16, 15, 24, 0.0F, false));

		BodyEnd = new ModelRenderer(this);
		BodyEnd.setRotationPoint(-0.25F, -2.25F, 28.0F);
		BodyMid.addChild(BodyEnd);
		BodyEnd.cubeList.add(new ModelBox(BodyEnd, 0, 0, -6.0F, -6.0F, -8.0F, 12, 12, 16, 0.0F, false));
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		Head.render(f5);
		Body.render(f5);
	}
	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}

	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity e) {
		super.setRotationAngles(f, f1, f2, f3, f4, f5, e);
		this.Head.rotateAngleY = f3 / (180F / (float) Math.PI);
		this.Head.rotateAngleX = f4 / (180F / (float) Math.PI);
		this.Body.rotateAngleZ = f3 / (180F / (float) Math.PI);
	}
}