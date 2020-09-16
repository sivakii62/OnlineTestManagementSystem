import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'filterTest'
})
export class FilterTestPipePipe implements PipeTransform {

  transform(testDetails: any, searchText1: any): any {
    if(searchText1){
      testDetails = testDetails.filter(test => test.testTitle.toLowerCase().startsWith(searchText1.toLowerCase()));
    }
    else {
      testDetails  = testDetails;
  }
  return testDetails;
  }

}
