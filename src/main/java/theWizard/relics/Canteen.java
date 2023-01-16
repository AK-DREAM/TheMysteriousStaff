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

public class Canteen extends CustomRelic {

    public static final String ID = DefaultMod.makeID("Canteen");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("Canteen.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("Canteen.png"));

    public Canteen() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.MAGICAL);
    }

    // Flash at the start of Battle.
    @Override
    public void atBattleStartPreDraw() {
        flash();
        AddManaAction.AddManaAction(2, true);
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
