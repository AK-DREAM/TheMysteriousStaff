package theWizard.relics;

import basemod.AutoAdd;
import basemod.BaseMod;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theWizard.DefaultMod;
import theWizard.actions.AddManaAction;
import theWizard.cards.Mana;
import theWizard.util.TextureLoader;
import static theWizard.DefaultMod.makeRelicOutlinePath;
import static theWizard.DefaultMod.makeRelicPath;

public class ToyBattery extends CustomRelic {

    public static final String ID = DefaultMod.makeID("ToyBattery");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("ToyBattery.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("ToyBattery.png"));

    public ToyBattery() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.CLINK);
    }

    @Override
    public void onEquip() {
        ++BaseMod.MAX_HAND_SIZE;
    }

    @Override
    public void onUnequip() {
        --BaseMod.MAX_HAND_SIZE;
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
