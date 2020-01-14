# Power of BDJ

This BD-J Xlet was created to demonstrate the possibility of using more memory than officially allowed. It allocates 32 1000x1000px `DVBBufferedImage`s, with each RGB pixel holding 24 bits of data, resulting in 96 MB total (instead of 6 MB available via normal arrays).

The calculation of 32 million powers of 3 (hence the name) and the verification of a result takes 2 seconds on my PS4, giving an effective BogoMIPS of 32.

## Compiling and running

This demo has been built using the so-called [Minimal PS3 BD-J SDK](https://mega.nz/#F!A4IFGYga!B6KAPlNBPBzGEN6j5OaDNQ), which has been [tweaked](https://playstationhax.xyz/forums/topic/5758-bd-j-devkit-how-do-i-burn-a-physical-disc/?do=findComment&comment=37495) to be compatible with a PS4.

## Math behind the sanity check

The program calculates `3**n` (i.e. `int("1", 3)`, `int("10", 3)`, `int("100", 3)`) for each `n=0..32000000`. Then it sums the powers for `n=0..31999999` to get `int("1"*32000000, 3)`. `int("1"*32000000, 3) * 2 + 1 == int("2"*32000000, 3) + 1 == int("1"+"0"*32000000) == 3**32000000`, which has been calculated earlier. All calculations are performed modulo `2**24`, as there are only 24 bits of precision in `DVBBufferedImage`.
