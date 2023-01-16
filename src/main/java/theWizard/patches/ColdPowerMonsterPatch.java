package theWizard.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theWizard.powers.ColdPower;

public class ColdPowerMonsterPatch {
    public ColdPowerMonsterPatch() {
    }

    @SpirePatch(
            clz = AbstractMonster.class,
            method = "damage",
            paramtypez = {DamageInfo.class}
    )
    public static class CheckColdPowerMonster {
        @SpireInsertPatch(
                rloc = 14,
                localvars = {"damageAmount"}
        )
        public static void Insert(AbstractMonster mo, DamageInfo info, @ByRef int[] damageAmount) {
            if (info.owner != null) {
                AbstractCreature p = info.owner;
                ColdPower tmp = (ColdPower)p.getPower("theWizard:ColdPower");
                if (tmp != null && info.type == DamageInfo.DamageType.NORMAL) {
                    int k = Math.min(damageAmount[0], tmp.amount);
                    tmp.ReduceColdPower(k);
                    damageAmount[0] -= k;
                }
            }
        }
    }

}
