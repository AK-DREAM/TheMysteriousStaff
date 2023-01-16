package theWizard.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import theWizard.powers.ColdPower;

public class ColdPowerPlayerPatch {
    public ColdPowerPlayerPatch() {
    }

    @SpirePatch(
            clz = AbstractPlayer.class,
            method = "damage",
            paramtypez = {DamageInfo.class}
    )
    public static class CheckColdPowerPlayer {
        @SpireInsertPatch(
                rloc = 13,
                localvars = {"damageAmount"}
        )
        public static void Insert(AbstractPlayer p, DamageInfo info, @ByRef int[] damageAmount) {
            if (info.owner != null) {
                AbstractCreature m = info.owner;
                ColdPower tmp = (ColdPower)m.getPower("theWizard:ColdPower");
                if (tmp != null && info.type == DamageInfo.DamageType.NORMAL) {
                    int k = Math.min(damageAmount[0], tmp.amount);
                    tmp.ReduceColdPower(k);
                    damageAmount[0] -= k;
                }
            }
        }
    }

}
