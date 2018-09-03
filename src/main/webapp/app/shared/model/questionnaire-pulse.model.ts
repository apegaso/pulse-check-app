import { Moment } from 'moment';

export interface IQuestionnairePulse {
    id?: number;
    dateStart?: Moment;
    dateEnd?: Moment;
    eventId?: number;
    participantId?: number;
}

export class QuestionnairePulse implements IQuestionnairePulse {
    constructor(
        public id?: number,
        public dateStart?: Moment,
        public dateEnd?: Moment,
        public eventId?: number,
        public participantId?: number
    ) {}
}
