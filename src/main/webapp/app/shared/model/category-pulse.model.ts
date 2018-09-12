import { ICategoryPulse } from 'app/shared/model//category-pulse.model';
import { IQuestionPulse } from 'app/shared/model//question-pulse.model';

export interface ICategoryPulse {
    id?: number;
    label?: string;
    fatherLabel?: string;
    fatherId?: number;
    levelLabel?: string;
    levelId?: number;
    sons?: ICategoryPulse[];
    questions?: IQuestionPulse[];
}

export class CategoryPulse implements ICategoryPulse {
    constructor(
        public id?: number,
        public label?: string,
        public fatherLabel?: string,
        public fatherId?: number,
        public levelLabel?: string,
        public levelId?: number,
        public sons?: ICategoryPulse[],
        public questions?: IQuestionPulse[]
    ) {}
}
