import { IEventPulse } from 'app/shared/model//event-pulse.model';

export interface IParticipantPulse {
    id?: number;
    userExtId?: number;
    events?: IEventPulse[];
}

export class ParticipantPulse implements IParticipantPulse {
    constructor(public id?: number, public userExtId?: number, public events?: IEventPulse[]) {}
}
