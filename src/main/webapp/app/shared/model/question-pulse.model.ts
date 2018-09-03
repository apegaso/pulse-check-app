export interface IQuestionPulse {
    id?: number;
    question?: string;
    categoryId?: number;
}

export class QuestionPulse implements IQuestionPulse {
    constructor(public id?: number, public question?: string, public categoryId?: number) {}
}
