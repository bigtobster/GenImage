# GenImage
GenImage is designed to generate a set of images that is a vague approximation of an existing set of an images. It is potentially useful for 
completing frames in animations, giving new ideas for patterns and paintings and to waste vast amounts of CPU clock cycles!

GenImage is built on a Genetic Algorithm and takes significant inspiration from the Resnick cycle of creativity.

This is a project developed in conjunction with University of Kent, England.

### Build

GenImage uses POM so building from source is super simple. Just run:

`mvn clean`

`mvn package`

The JAR, complete with shaded dependencies will appear in ./target

### Execution

Basic execution is via the normal Java JAR invocation line:

`java -jar <INSERT JAR HERE>`

However, GenImage requires a whole heap of arguments. It will give you a bit of help on them though but just running the above. It will print out a
 help screen for you to start working out what you need.
 
### Get Started

To save you trying to work out how it all works, here is a line just to get you started.

**This method assumes you have images in the same directory as the JAR in a directory called "images". If this isn't true, bad things WILL happen!**

``java -jar GenImage-1.0-SNAPSHOT.jar -boxSize=3 -crossover=0.8 -dump=5 -iterations=25 -mutation=0.1 -noveltyBarrier=0.75 -populationSize=25 
-retention=0.5 -input=images/ -tournamentSize=3``

If you need some images, you can find some in the src tree. See `src/test/resources`. Pro tip: Use the LowRes images for improved performance.

### Help

The below is a straight lift from the application help screen

`usage: GenImage`
`
    --boxSize <b>          The amount of pixel placement tolerance in fitness comparison`
`    --crossover <c>        The probability that a candidate will be selected for crossover`
    `--dump <d>             The number of best candidates to export`
    `--help                 Display help and usage info`
    `--input <i>            Path to directory containing images to import`
    `--iterations <l>       The number of iterations before dumping results and awaiting further instructions`
    `--mutation <m>         The probability that a candidate will be mutated`
    `--noveltyBarrier <n>   The minimum percentage of pixels from a single input image which a candidate must match before being eliminated due to lack of novelty`
    `--populationSize <s>   The size of the candidate population`
    `--retention <r>        The percentage (as a decimal) of the population to retain during tournament selection`
    `--tournamentSize <t>   The number of candidates in the tournaments during Tournament selection`

### Tips!

* The process becomes MASSIVELY more intensive on large images in particular. Use LowRes for quick results.
* The output is a bit abstract at the moment although it does definitely work. For best results, run lots and lots of cycles (at least several 
hundred). After a while, you should start to see patterns emerging like strips of congealed colour that look suspiciously like well placed lines. 
You may also notice parts of the image be slightly more biased to certain colours. This affect increases as you run more cycles.
* The interface is quite unforgiving. It will only run if it's sane. Use the example provided above to get started.
* If you're experimenting with some flags, note that this is a work in progress and so some are disabled or partially implemented
* You can read about genetic algorithms here: https://en.wikipedia.org/wiki/Genetic_algorithm