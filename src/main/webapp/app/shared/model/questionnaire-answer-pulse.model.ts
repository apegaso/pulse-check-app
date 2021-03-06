export interface IQuestionnaireAnswerPulse {
    id?: number;
    importance?: number;
    performance?: number;
    note?: string;
    questionnaireId?: number;
    questionId?: number;
}

export class QuestionnaireAnswerPulse implements IQuestionnaireAnswerPulse {
    constructor(
        public id?: number,
        public importance?: number,
        public performance?: number,
        public note?: string,
        public questionnaireId?: number,
        public questionId?: number
    ) {}
}
