export class LoggingService
{
    logStatus(message : string)
    {
        let CurrentDateTime = new Date();
        let CurrentDateTimeString = CurrentDateTime.toString();console.log(`${(CurrentDateTimeString)} : ` , message);

    }
}