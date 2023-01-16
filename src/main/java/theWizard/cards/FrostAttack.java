package theWizard.cards;

import basemod.AutoAdd;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import theWizard.DefaultMod;
import theWizard.characters.TheWizard;
import theWizard.powers.ColdPower;

import java.util.Iterator;

import static theWizard.DefaultMod.makeCardPath;

public class FrostAttack extends AbstractDynamicCard {
    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(FrostAttack.class.getSimpleName());
    public static final String IMG = makeCardPath("FrostAttack.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.ENEMY;  //   since they don't change much.
    private static final CardType TYPE = CardType.ATTACK;       //
    public static final CardColor COLOR = TheWizard.Enums.COLOR_GRAY;

    private static final int COST = 0;
    private static final int UPGRADED_COST = 0;

    private static final int DAMAGE = 3;
    private static final int MAGIC = 3;
    private static final int TIMES = 4;
    // /STAT DECLARATION/


    public FrostAttack() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC;
        defaultBaseSecondMagicNumber = defaultSecondMagicNumber = TIMES;
    }
    // Actions the card should do.
    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean cc = super.canUse(p, m);
        if (!cc) return false;
        else return defaultSecondMagicNumber > 0;
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(
                new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        this.addToBot(new ApplyPowerAction(m, p, new ColdPower(m, p, magicNumber)));
        --defaultBaseSecondMagicNumber; --defaultSecondMagicNumber;
        isDefaultSecondMagicNumberModified = true;
        this.initializeDescription();
        // patch at FrostAttackPatch
    }
    @Override
    public void atTurnStart() {
        defaultBaseSecondMagicNumber = defaultSecondMagicNumber = TIMES;
        isDefaultSecondMagicNumberModified = false;
        this.initializeDescription();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADED_COST);
            upgradeDamage(1);
            upgradeMagicNumber(1);
            initializeDescription();
        }
    }
}
