import { ICategoryPulse } from 'app/shared/model//category-pulse.model';

export interface IQuestionPulse {
    id?: number;
    question?: string;
    order?: number;
    subQuestion?: string;
    importanceScoreActive?: boolean;
    performanceScoreActive?: boolean;
    showQuestion?: boolean;
    groupId?: number;
    categories?: ICategoryPulse[];
}

export class QuestionPulse implements IQuestionPulse {
    constructor(
        public id?: number,
        public question?: string,
        public order?: number,
        public subQuestion?: string,
        public importanceScoreActive?: boolean,
        public performanceScoreActive?: boolean,
        public showQuestion?: boolean,
        public groupId?: number,
        public categories?: ICategoryPulse[]
    ) {
        this.importanceScoreActive = this.importanceScoreActive || false;
        this.performanceScoreActive = this.performanceScoreActive || false;
        this.showQuestion = this.showQuestion || false;
    }
}
