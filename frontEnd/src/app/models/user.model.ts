import { Time } from '@angular/common';

export class User{
    userId:number;
    userName:string;
    isAdmin:boolean;
    userPassword:string;
    emailId:string;
    isActiveTest:boolean = false;
    userTest:User_Test[];
	
}

export class Question{
	questionId:number;
    questionOptions:String[];
    questionTitle:string;
    questionAnswer:number;
    questionMarks:number;
    questionCategory:Category;
    testQuestions:Test;

}
export class Test{
    test_Id:number;
    testTitle:string;
    testDuration:number;
    totalQuestion:number;
    testTotalMarks:number;
    startDate:Time;
    endDate:Time;
    allQuestion:Question[];
    testStatus:number;	
    userTest:User_Test[];

}
export class Category{
    categoryId:number;
    name:string;
    allQuestion:Question[];

}
export class User_Test{
    user_Test_Id:number;
    user:User;
    test:Test;
    totalAttempt:number;
    UsertestAnswer:number[];
    testCorrectAnswer:boolean[];
    isAttempted:boolean;
    isDeclared:boolean;
 
}

export class CategoryResult{
    categoryResultId:number;
    userTest:User_Test;
    category:Category;
    categoryResult:number;
    attemptedQuestions:number;
}
export class TopResults{
    user:string;
    test:string;
    attempt:number;
}

export class MyJwt{
    jwt:string;
    role:boolean;
}
