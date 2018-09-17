export interface IQuestionGroupPulse {
    id?: number;
    questionNumber?: number;
}

export class QuestionGroupPulse implements IQuestionGroupPulse {
    constructor(public id?: number, public questionNumber?: number) {}
}
