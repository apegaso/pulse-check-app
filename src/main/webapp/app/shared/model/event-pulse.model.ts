import { Moment } from 'moment';
import { IParticipantPulse } from 'app/shared/model//participant-pulse.model';
import { IClientLeadPulse } from 'app/shared/model//client-lead-pulse.model';

export interface IEventPulse {
    id?: number;
    eventName?: string;
    eventDescription?: string;
    eventDate?: Moment;
    closed?: boolean;
    organizationOrganizationName?: string;
    organizationId?: number;
    participants?: IParticipantPulse[];
    leads?: IClientLeadPulse[];
}

export class EventPulse implements IEventPulse {
    constructor(
        public id?: number,
        public eventName?: string,
        public eventDescription?: string,
        public eventDate?: Moment,
        public closed?: boolean,
        public organizationOrganizationName?: string,
        public organizationId?: number,
        public participants?: IParticipantPulse[],
        public leads?: IClientLeadPulse[]
    ) {
        this.closed = this.closed || false;
    }
}
