DISTRIBUTION NOTES:

This distribution contains the comm.jar file original part
of the COMMAPI 2.0.3 release.  The 2.0.3 release itself is
no longer distributed.  comm.jar, which contains the comm
API java class files (eg. Sun's binary implementation of the 
interface classes), is being made available, unsupported,
with no obligation to fix bugs, at the request of the RXTX 
project.  The RxTx project (http://www.rxtx.org), uses the
comm.jar class files, but provides their own native library
components to provide support for other platforms.

The 2.0.3 comm.jar is being provided in this way because 
as of COMMAPI 3.x, the java binary implementation forked and 
became incompatible with the RxTx project's native code. 
Due to the lack of a clear business case, and to resource 
constraints, no additional resources are being 
provided to provide an alternative engineering solution
at the time of writing.

RECOMMENDATIONS FOR THE FUTURE OF COMMAPI:

Ultimately the COMMAPI would be better served if a Java 
Community Process JSR (http://www.jcp.org) was opened and 
the COMMAPI was rearchitected to provide a more pluggable
framework to improve cross-platform support.  Such an
architecture would embody a better defined and better 
documented SPI interface, including a way for comm. ports 
to be revealed to the client application through the API
via platform-aware plugins.  Similarly, native code used
to access comm port in general could be handled more 
flexibly using a plugin model, whereby on any platform,
multiple native modules could be in use simultaneously.
 
Since there are now various ways serial ports can be implemented 
and accessed, modular comm. port JNI native libraries would be 
of benefit insofar as removing the burden of over-generalization
from any single native component, or inversely, by relieving the
implicit constraint that comm. drivers be accessed only via a
monolithic mechanism, such as the UNIX vnode interface.


paul.klissner@sun.com
6/10/06

