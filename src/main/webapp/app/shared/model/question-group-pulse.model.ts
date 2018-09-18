import { IQuestionPulse } from 'app/shared/model//question-pulse.model';

export interface IQuestionGroupPulse {
    id?: number;
    questionNumber?: number;
    questions?: IQuestionPulse[];
}

export class QuestionGroupPulse implements IQuestionGroupPulse {
    constructor(public id?: number, public questionNumber?: number, public questions?: IQuestionPulse[]) {}
}
