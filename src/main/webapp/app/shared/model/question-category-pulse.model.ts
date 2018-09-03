import { IQuestionCategoryPulse } from 'app/shared/model//question-category-pulse.model';
import { IQuestionPulse } from 'app/shared/model//question-pulse.model';

export interface IQuestionCategoryPulse {
    id?: number;
    label?: string;
    level?: number;
    soons?: IQuestionCategoryPulse[];
    fatherId?: number;
    fatherId?: number;
    soons?: IQuestionCategoryPulse[];
    questions?: IQuestionPulse[];
}

export class QuestionCategoryPulse implements IQuestionCategoryPulse {
    constructor(
        public id?: number,
        public label?: string,
        public level?: number,
        public soons?: IQuestionCategoryPulse[],
        public fatherId?: number,
        public fatherId?: number,
        public soons?: IQuestionCategoryPulse[],
        public questions?: IQuestionPulse[]
    ) {}
}
