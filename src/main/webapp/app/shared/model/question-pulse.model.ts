import { ICategoryPulse } from 'app/shared/model//category-pulse.model';

export interface IQuestionPulse {
    id?: number;
    question?: string;
    order?: number;
    categories?: ICategoryPulse[];
}

export class QuestionPulse implements IQuestionPulse {
    constructor(public id?: number, public question?: string, public order?: number, public categories?: ICategoryPulse[]) {}
}
