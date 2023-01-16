package theWizard.relics;

import basemod.AutoAdd;
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

public class DarkMagicBook extends CustomRelic {

    public static final String ID = DefaultMod.makeID("DarkMagicBook");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("DarkMagicBook.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("DarkMagicBook.png"));

    public DarkMagicBook() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.FLAT);
    }

    @Override
    public void onEquip() {
        ++AbstractDungeon.player.energy.energyMaster;
    }

    @Override
    public void onUnequip() {
        --AbstractDungeon.player.energy.energyMaster;
    }

    @Override
    public boolean canPlay(AbstractCard card) {
        if (card instanceof Mana) return false;
        else return true;
    }
    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
