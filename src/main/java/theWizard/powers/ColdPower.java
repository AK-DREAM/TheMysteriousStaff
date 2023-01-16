package theWizard.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import theWizard.DefaultMod;
import theWizard.cards.AbstractDynamicCard;
import theWizard.cards.Mana;
import theWizard.util.TextureLoader;

import static theWizard.DefaultMod.makePowerPath;


public class ColdPower extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = DefaultMod.makeID("ColdPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("ColdPower_84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("ColdPower_32.png"));

    public ColdPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        type = PowerType.DEBUFF;
        isTurnBased = false;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    // I have patched it at theWizard.patches.ColdPowerMonsterPatch !

    public void ReduceColdPower(int rmv) {
        int dmg = rmv;
        this.addToTop(new ReducePowerAction(this.owner, this.owner, POWER_ID, rmv));
        this.addToTop(new DamageAction(this.owner, new DamageInfo(null, dmg, DamageInfo.DamageType.THORNS)));
    }

    @Override
    public void atEndOfRound() {
        if (this.amount <= 0) {
            this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
            return;
        }
        if (this.owner instanceof AbstractPlayer) {
            int rmv = (int)Math.ceil(0.5*this.amount);
            this.addToBot(new ReducePowerAction(this.owner, this.owner, POWER_ID, rmv));
        } else if (!AbstractDungeon.player.hasPower("theWizard:AbsoluteZeroPower")) {
            int rmv = (int)Math.ceil(0.5*this.amount);
            if (AbstractDungeon.player.hasRelic("theWizard:IceCube")) {
                rmv = Math.min(rmv, 15);
            }
            if (AbstractDungeon.player.hasPower("theWizard:ColdDisasterPower")) {
                this.ReduceColdPower(rmv);
            } else {
                this.addToBot(new ReducePowerAction(this.owner, this.owner, POWER_ID, rmv));
            }
        }
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }

    @Override
    public AbstractPower makeCopy() {
        return new ColdPower(owner, source, amount);
    }
}
