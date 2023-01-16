package theWizard.relics;

import basemod.AutoAdd;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.unique.ArmamentsAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theWizard.DefaultMod;
import theWizard.actions.AddManaAction;
import theWizard.util.TextureLoader;
import static theWizard.DefaultMod.makeRelicOutlinePath;
import static theWizard.DefaultMod.makeRelicPath;

public class MagicHammer extends CustomRelic {

    public static final String ID = DefaultMod.makeID("MagicHammer");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("MagicHammer.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("MagicHammer.png"));

    public MagicHammer() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.HEAVY);
    }

    @Override
    public void atBattleStart() {
        flash();
        this.addToBot(new ArmamentsAction(true));
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
