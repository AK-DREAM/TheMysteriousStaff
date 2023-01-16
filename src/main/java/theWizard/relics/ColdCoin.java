package theWizard.relics;

import basemod.AutoAdd;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theWizard.DefaultMod;
import theWizard.actions.AddManaAction;
import theWizard.powers.ColdPower;
import theWizard.util.TextureLoader;
import static theWizard.DefaultMod.makeRelicOutlinePath;
import static theWizard.DefaultMod.makeRelicPath;

public class ColdCoin extends CustomRelic {

    public static final String ID = DefaultMod.makeID("ColdCoin");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("ColdCoin.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("ColdCoin.png"));

    public ColdCoin() {
        super(ID, IMG, OUTLINE, RelicTier.SHOP, LandingSound.CLINK);
    }

    @Override
    public void onPlayerEndTurn() {
        flash();
        AbstractPlayer p = AbstractDungeon.player;
        int k = p.gold / 100;
        for (final AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            this.addToBot(new ApplyPowerAction(mo, p, new ColdPower(mo, p, k)));
        }
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
