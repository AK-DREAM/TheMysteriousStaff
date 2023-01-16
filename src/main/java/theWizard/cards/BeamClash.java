package theWizard.cards;

import basemod.AutoAdd;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.MindblastEffect;
import theWizard.DefaultMod;
import theWizard.actions.CheckManaCostAction;
import theWizard.characters.TheWizard;

import static theWizard.DefaultMod.makeCardPath;

public class BeamClash extends AbstractDynamicCard {
    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(BeamClash.class.getSimpleName());
    public static final String IMG = makeCardPath("BeamClash.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;  //   since they don't change much.
    private static final CardType TYPE = CardType.ATTACK;       //
    public static final CardColor COLOR = TheWizard.Enums.COLOR_GRAY;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final int COST = 1;
    private static final int UPGRADED_COST = 1;

    private static final int DAMAGE = 30;
    private static final int UPGRADE_PLUS_DMG = 6;
    private static final int MANA_COST = 2;

    public int[] multidamage2, tmp;
    // /STAT DECLARATION/


    public BeamClash() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = damage = DAMAGE;
        baseMagicNumber = magicNumber = 10;
        isMultiDamage = true;
    }

    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        if (AbstractDungeon.player.hand.size() >= 10) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        }
    }

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean cc = super.canUse(p, m);
        if (!cc) {
            return false;
        } else if (AbstractDungeon.player.hand.size() < 10) {
            this.cantUseMessage = UPGRADE_DESCRIPTION;
            return false;
        } else return true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new VFXAction(new MindblastEffect(p.dialogX, p.dialogY, false)));
        tmp = multiDamage;
        if (CheckManaCostAction.CheckManaCost(MANA_COST)) {
            for (int i = 0; i < tmp.length; i++) tmp[i] += multidamage2[i];
        }
        this.addToBot(new DamageAllEnemiesAction(p, tmp, this.damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));
    }

    @Override
    public void applyPowers() {
        this.baseDamage -= this.upgraded?22:20;
        this.baseMagicNumber = this.baseDamage;
        super.applyPowers();
        multidamage2 = multiDamage;
        this.magicNumber = this.damage;
        this.isMagicNumberModified = this.isDamageModified;
        this.baseDamage += this.upgraded?22:20;
        super.applyPowers();
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        this.baseDamage -= this.upgraded?22:20;
        this.baseMagicNumber = this.baseDamage;
        super.calculateCardDamage(mo);
        multidamage2 = multiDamage;
        this.magicNumber = this.damage;
        this.isMagicNumberModified = this.isDamageModified;
        this.baseDamage += this.upgraded?22:20;
        super.calculateCardDamage(mo);
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            upgradeBaseCost(UPGRADED_COST);
            this.baseMagicNumber = 14;
            this.upgradedMagicNumber = this.upgradedDamage;
            initializeDescription();
        }
    }
}
