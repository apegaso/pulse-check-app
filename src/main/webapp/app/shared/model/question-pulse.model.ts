import { IQuestionCategoryPulse } from 'app/shared/model//question-category-pulse.model';

export interface IQuestionPulse {
    id?: number;
    question?: string;
    categories?: IQuestionCategoryPulse[];
}

export class QuestionPulse implements IQuestionPulse {
    constructor(public id?: number, public question?: string, public categories?: IQuestionCategoryPulse[]) {}
}
