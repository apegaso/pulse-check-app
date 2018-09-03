import { Moment } from 'moment';
import { IOrganizationPulse } from 'app/shared/model//organization-pulse.model';
import { IParticipantPulse } from 'app/shared/model//participant-pulse.model';
import { IClientLeadPulse } from 'app/shared/model//client-lead-pulse.model';

export interface IEventPulse {
    id?: number;
    eventName?: string;
    eventDate?: Moment;
    organizations?: IOrganizationPulse[];
    participants?: IParticipantPulse[];
    leads?: IClientLeadPulse[];
}

export class EventPulse implements IEventPulse {
    constructor(
        public id?: number,
        public eventName?: string,
        public eventDate?: Moment,
        public organizations?: IOrganizationPulse[],
        public participants?: IParticipantPulse[],
        public leads?: IClientLeadPulse[]
    ) {}
}
