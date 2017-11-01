#!/bin/bash
##
## CA Utility to wrap APK file with mobile analytics
##
starttime=$(date +%s)
echo
echo "|--------------------------------------- CA Mobile App Analytics --------------------------------------------------|"
culimit=$(ulimit -a |grep -i "open files" | awk  '{print $4}')
tulimit=65535
echo "Current Ulimit $culimit"
echo "Setting file open to $tulimit"
ulimit -n $tulimit
echo "After setting ulimit $(ulimit -a |grep -i "open files" | awk  '{print $4}')"



usage()
{
cat << EOF

usage: $0 -a <apk file> -p <plist> [options]

This script wraps the apk file in CA MAA Container

options:
   -h   Show this message
   -w   Intercept web requests only using android webkit api.
   -d   Use SDK build-type debug. Optional, default value is release.
   -c   Specify Jarsigner Configuration.
   -r   Do not decompile resources in APK file
   -s   Do not sign the wrapped APK file
   -o   Output apk file name. By default wrappered filename will be wrapped_${apk_filename}.apk
   -i   <instrumentation xml>
        Instrumentation rules xml file.  By default wrapper auto detects instrumentation rules.
   -j   <Wrapper jar file>
        CA MAA Android wrapper jar file.  Default is ca-maa-android-sdk-wrapper-<version>.jar
   -v   Verbose

EOF
exit -1;
}


checkfileexistence()
{
   echo "checking for $1 ..."
   if [ ! -f "$1" ]
   then
      echo "Wrapping Failure :  Wrapping unsuccessful"
      echo "Wrapping Failure :  $1 not found."
      usage 
   fi
}



OUTPUT_FILE=""
APKFILE=
PLIST=
NORES=
NOSIGN=
SIGNCONFIG=emm/conf/jarsigner.properties
RULES=AUTO_DETECT
JAR=ca-maa-android-sdk-wrapper-17.2.jar
VERBOSE=
BUILD_TYPE="release"
JS_INTERCEPTION_DISABLED="false"

while getopts “a:p:dwhrtsvi:j:c:k:l:o:” OPTION
do
     case $OPTION in
         h)
             usage
             ;;
         a)
             APKFILE=$OPTARG
             ;;
         d)
             BUILD_TYPE="debug"
             ;;
         w)
            JS_INTERCEPTION_DISABLED="true"
            ;;
         p)
             PLIST=$OPTARG
             ;;
         o)
             OUTPUT_FILE=$OPTARG
             ;;
         c)
             SIGNCONFIG=$OPTARG
             ;;
         r)
             NORES=-nores
             ;;
         i)
             RULES=$OPTARG
             ;;
         j)
             JAR=$OPTARG
             ;;
         s)
             NOSIGN=-nosign
             ;;
         v)
             VERBOSE=-verbose
             ;;
     esac
done

checkJarSigner()
{
   echo "checking for $1 ..."
   if [ ! -f "$1" ]
   then
      echo "Jarsigner is not present , wrapping without signing."
      NOSIGN=-nosign
   fi
}

# If passed in values are not right then exit
if [ $# -lt 2 ]
then
   usage
fi

# check if JAVA_HOME is set, if not bail out early!
if [[ -z "$JAVA_HOME" ]]
then
echo "JAVA_HOME not set."
exit -1; 
fi
echo
echo "Validating Inputs :"
echo "******************************"

checkJarSigner $JAVA_HOME/bin/jarsigner
checkfileexistence  "$SIGNCONFIG"
checkfileexistence "$APKFILE" 
checkfileexistence "$PLIST" 
checkfileexistence $JAR



#Set CA_EMM_HOME
# Can be set on java command line also  like this -Dca.emm.home=<path>
echo 
echo "Provided Configuration : "
echo "******************************"
export CA_EMM_HOME=`pwd`/emm

echo "EMM HOME : $CA_EMM_HOME"

echo "JAVA_HOME : $JAVA_HOME"

# Display parameters
echo "Apk File : $APKFILE"
echo "Plist File : $PLIST"
echo "Jarsigner Configuration : $SIGNCONFIG"
echo "Wrapper Jar : $JAR"

echo 

echo "Wrapping Process:"
echo "******************************"

#Run the command
# JAVA_DEBUG=-agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=6006

OUTPUT_FILE_ARG=""
if [[ -z "$OUTPUT_FILE" ]]
then
    OUTPUT_FILE_ARG=""
else
    OUTPUT_FILE_ARG="-outputFile $OUTPUT_FILE"
fi

(export PATH=$JAVA_HOME/bin:$PATH

echo $JAVA_HOME/bin/java -jar $JAVA_DEBUG $JAR $VERBOSE -apk "$APKFILE" -plist "$PLIST" -rules $RULES -signconfig $SIGNCONFIG -disablejsinterception ${JS_INTERCEPTION_DISABLED} -buildtype $BUILD_TYPE  $NORES $NOSIGN $BUILD_TYPE $OUTPUT_FILE_ARG
echo 
$JAVA_HOME/bin/java -jar $JAVA_DEBUG $JAR $VERBOSE -apk "$APKFILE" -plist "$PLIST" -rules $RULES -signconfig $SIGNCONFIG -disablejsinterception ${JS_INTERCEPTION_DISABLED} -buildtype $BUILD_TYPE $NORES  $NOSIGN $BUILD_TYPE $OUTPUT_FILE_ARG
)
echo 
endtime=$(date +%s)
runtimetaken=$((endtime-starttime))
echo "Total time : $runtimetaken secs"
echo
echo "Setting file open back to $culimit"
ulimit -n $culimit
echo "After restoring ulimit $(ulimit -a |grep -i "open files" | awk  '{print $4}')"

if [ "$NOSIGN" = "-nosign" ] 
then
echo "warning: wrapped apk is not signed. Please sign with your certificate.";
fi

echo "|----------------------------------------- © 2016 CA Technologies. All rights reserved. ----------------------------------------------------|"

