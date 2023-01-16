package theWizard.cards;

import basemod.AutoAdd;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;
import theWizard.DefaultMod;
import theWizard.characters.TheWizard;

import static theWizard.DefaultMod.makeCardPath;

public class Combo extends AbstractDynamicCard {
    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(Combo.class.getSimpleName());
    public static final String IMG = makeCardPath("Combo.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.ENEMY;  //   since they don't change much.
    private static final CardType TYPE = CardType.ATTACK;       //
    public static final CardColor COLOR = TheWizard.Enums.COLOR_GRAY;

    private static final int COST = 3;
    private static final int UPGRADED_COST = 3;

    private static final int DAMAGE = 6;
    private static final int UPGRADE_PLUS_DMG = 2;
    private int MaxCost;
    // /STAT DECLARATION/


    public Combo() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE; MaxCost = COST;
    }

    @Override
    public void resetAttributes() {
        super.resetAttributes();
        this.MaxCost = this.cost;
    }
    @Override
    public void applyPowers() {
        super.applyPowers();
        AbstractPower tmp = AbstractDungeon.player.getPower("theWizard:ManaExhaustedThisTurn");
        if (tmp != null) {
            int Dec = tmp.amount;
            this.setCostForTurn(Math.max(0, Math.min(this.cost-Dec, this.MaxCost))+100);
        }
    }
    @Override
    public void setCostForTurn(int amt) {
        if (amt >= 100) amt -= 100;
        else this.MaxCost = Math.min(this.MaxCost,amt);
        if (this.costForTurn >= 0) {
            this.costForTurn = amt;
            if (this.costForTurn < 0) {
                this.costForTurn = 0;
            }
            if (this.costForTurn != this.cost) {
                this.isCostModifiedForTurn = true;
            }
        }

    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 1; i <= 3; i++) {
            this.addToBot(new SFXAction("ORB_LIGHTNING_EVOKE", 0.1F));
            this.addToBot(
                    new VFXAction(new LightningEffect(m.drawX, m.drawY), 0.0F));
            this.addToBot(
                    new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
        }
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            upgradeBaseCost(UPGRADED_COST);
            initializeDescription();
        }
    }
}
