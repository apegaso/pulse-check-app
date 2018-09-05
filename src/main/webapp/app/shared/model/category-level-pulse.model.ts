import { ICategoryPulse } from 'app/shared/model//category-pulse.model';

export interface ICategoryLevelPulse {
    id?: number;
    label?: string;
    categories?: ICategoryPulse[];
}

export class CategoryLevelPulse implements ICategoryLevelPulse {
    constructor(public id?: number, public label?: string, public categories?: ICategoryPulse[]) {}
}
