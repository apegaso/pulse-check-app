import { IQuestionCategoryPulse } from 'app/shared/model//question-category-pulse.model';

export interface IQuestionCategoryPulse {
    id?: number;
    label?: string;
    level?: number;
    soonsId?: number;
    questionsId?: number;
    fathers?: IQuestionCategoryPulse[];
}

export class QuestionCategoryPulse implements IQuestionCategoryPulse {
    constructor(
        public id?: number,
        public label?: string,
        public level?: number,
        public soonsId?: number,
        public questionsId?: number,
        public fathers?: IQuestionCategoryPulse[]
    ) {}
}
