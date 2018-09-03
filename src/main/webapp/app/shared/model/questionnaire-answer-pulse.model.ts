export interface IQuestionnaireAnswerPulse {
    id?: number;
    importance?: number;
    performance?: number;
    questionaireId?: number;
    questionId?: number;
}

export class QuestionnaireAnswerPulse implements IQuestionnaireAnswerPulse {
    constructor(
        public id?: number,
        public importance?: number,
        public performance?: number,
        public questionaireId?: number,
        public questionId?: number
    ) {}
}
