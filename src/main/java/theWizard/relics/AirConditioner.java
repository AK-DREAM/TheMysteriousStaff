package theWizard.relics;

import basemod.AutoAdd;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theWizard.DefaultMod;
import theWizard.actions.AddManaAction;
import theWizard.cards.Mana;
import theWizard.powers.NoCostPower;
import theWizard.util.TextureLoader;
import static theWizard.DefaultMod.makeRelicOutlinePath;
import static theWizard.DefaultMod.makeRelicPath;

public class AirConditioner extends CustomRelic {

    public static final String ID = DefaultMod.makeID("AirConditioner");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("AirConditioner.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("AirConditioner.png"));

    public AirConditioner() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.HEAVY);
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
