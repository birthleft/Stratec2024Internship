package org.birthleft.validator;

import org.birthleft.exception.CooldownTimeValidatorException;

public class CooldownTimeValidator {
    public static Integer compute(String cooldownTime, Integer line) {
        String[] cooldownTimeChunks = cooldownTime.split(" ", 2);
        Integer result = -1;
        if (cooldownTimeChunks.length == 1) {
            if (!cooldownTimeChunks[0].trim().equals("none")) {
                throw new CooldownTimeValidatorException(line, "The only way to have only one word is with 'none'!");
            }
            result = 0;
        }
        else if (cooldownTimeChunks.length == 2) {
            if (!cooldownTimeChunks[1].trim().equals("seconds after each part")) {
                throw new CooldownTimeValidatorException(line, "If there is a cooldown time, it must be followed by 'seconds after each part'!");
            }
            try {
                result = Integer.parseInt(cooldownTimeChunks[0]);
            } catch (NumberFormatException e) {
                throw new CooldownTimeValidatorException(line, "Cooldown time must be a number!");
            }
        }
        return result;
    }
}
