package theWizard.relics;

import basemod.AutoAdd;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theWizard.DefaultMod;
import theWizard.actions.AddManaAction;
import theWizard.util.TextureLoader;

import static theWizard.DefaultMod.makeRelicOutlinePath;
import static theWizard.DefaultMod.makeRelicPath;

public class IceCube extends CustomRelic {

    public static final String ID = DefaultMod.makeID("IceCube");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("IceCube.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("IceCube.png"));

    public IceCube() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.SOLID);
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
