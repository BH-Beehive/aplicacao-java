package utils;

import java.util.concurrent.TimeUnit;


public class Conversor {

    private long KIBI = 1L << 10;
    private long MEBI = 1L << 20;
    private long GIBI = 1L << 30;
    private long TEBI = 1L << 40;
    private long PEBI = 1L << 50;
    private long EXBI = 1L << 60;

    public String formatarBytes(long bytes) {
        if (bytes == 1L) { // bytes
            return String.format("%d byte", bytes);
        } else if (bytes < KIBI) { // bytes
            return String.format("%d bytes", bytes);
        } else if (bytes < MEBI) { // KiB
            return formatarUnidades(bytes, KIBI, "KiB");
        } else if (bytes < GIBI) { // MiB
            return formatarUnidades(bytes, MEBI, "MiB");
        } else if (bytes < TEBI) { // GiB
            return formatarUnidades(bytes, GIBI, "GiB");
        } else if (bytes < PEBI) { // TiB
            return formatarUnidades(bytes, TEBI, "TiB");
        } else if (bytes < EXBI) { // PiB
            return formatarUnidades(bytes, PEBI, "PiB");
        } else { // EiB
            return formatarUnidades(bytes, EXBI, "EiB");
        }
    }

    public String formatarSegundosDecorridos(long secs) {

        long eTime = secs;

        final long days = TimeUnit.SECONDS.toDays(eTime);
        eTime -= TimeUnit.DAYS.toSeconds(days);

        final long hr = TimeUnit.SECONDS.toHours(eTime);
        eTime -= TimeUnit.HOURS.toSeconds(hr);

        final long min = TimeUnit.SECONDS.toMinutes(eTime);
        eTime -= TimeUnit.MINUTES.toSeconds(min);

        final long sec = eTime;

        return String.format("%d days, %02d:%02d:%02d", days, hr, min, sec);
    }

    public String formatarUnidades(long valor, long prefixo, String unidade) {
        if (valor % prefixo == 0) {
            return String.format("%d %s", valor / prefixo, unidade);
        }
        return String.format("%.1f %s", (double) valor / prefixo, unidade);
    }

    public Long formatarUnidades(long valor, long prefixo) {
        if (valor % prefixo == 0) {
            return valor / prefixo;
        }
        return valor / prefixo;
    }

    public long getKIBI() {
        return KIBI;
    }

    public long getMEBI() {
        return MEBI;
    }

    public long getGIBI() {
        return GIBI;
    }

    public long getTEBI() {
        return TEBI;
    }

    public long getPEBI() {
        return PEBI;
    }

    public long getEXBI() {
        return EXBI;
    }

}