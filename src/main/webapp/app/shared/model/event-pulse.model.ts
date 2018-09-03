import { Moment } from 'moment';
import { IParticipantPulse } from 'app/shared/model//participant-pulse.model';
import { IClientLeadPulse } from 'app/shared/model//client-lead-pulse.model';

export interface IEventPulse {
    id?: number;
    eventName?: string;
    eventDate?: Moment;
    organizationId?: number;
    participants?: IParticipantPulse[];
    leads?: IClientLeadPulse[];
}

export class EventPulse implements IEventPulse {
    constructor(
        public id?: number,
        public eventName?: string,
        public eventDate?: Moment,
        public organizationId?: number,
        public participants?: IParticipantPulse[],
        public leads?: IClientLeadPulse[]
    ) {}
}
