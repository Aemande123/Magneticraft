package com.cout970.magneticraft.client.tilerender;

import com.cout970.magneticraft.client.model.ModelConstants;
import com.cout970.magneticraft.tileentity.kinetic.TileKineticGrinder;
import com.cout970.magneticraft.util.MiscUtils;
import net.darkaqua.blacksmith.modelloader.techne.TechneDynamicModel;
import net.darkaqua.blacksmith.render.model.IDynamicModel;
import net.darkaqua.blacksmith.vectors.Vect3d;
import org.lwjgl.opengl.GL11;

/**
 * Created by cout970 on 29/01/2016.
 */
public class TileRenderKineticGrinder extends TileRenderer<TileKineticGrinder> {

    private static IDynamicModel model;
    private static IDynamicModel.IPartSet base;
    private static IDynamicModel.IPartSet rshaft;
    private static IDynamicModel.IPartSet lshaft;

    @Override
    public void renderTileEntity(TileKineticGrinder def, Vect3d offset, float partialTick, int breakingProgress) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float) offset.getX(), (float) offset.getY(), (float) offset.getZ());
        float rotation = def.getRotationAngle(partialTick)*4;
        GL11.glTranslatef(0.5f, 0, 0.5f);
        GL11.glRotatef(MiscUtils.getRotation(def.getDirection()) + 90, 0, 1, 0);
        GL11.glTranslatef(-0.5f, 0, -0.5f);
        model.renderPartSet(base);
        float dist = 0.0625f*2;
        GL11.glPushMatrix();
        GL11.glTranslatef(0, 0.5f, 0.5f-dist);
        GL11.glRotatef(rotation, 1, 0, 0);
        GL11.glTranslatef(0, -0.5f, -0.5f+(0.0625f*1.5f));
        model.renderPartSet(lshaft);
        GL11.glPopMatrix();
        GL11.glTranslatef(0, 0.5f, 0.5f+dist);
        GL11.glRotatef(-rotation, 1, 0, 0);
        GL11.glTranslatef(0, -0.5f, -0.5f-(0.0625f*1.5f));
        model.renderPartSet(rshaft);

        GL11.glPopMatrix();
    }

    @Override
    public void initModels() {
        model = new TechneDynamicModel(ModelConstants.KINETIC_GRINDER);
        base = model.createAllContains("base");
        lshaft = model.createAllContains("Wheell");
        rshaft = model.createAllContains("Wheelr");
    }
}
