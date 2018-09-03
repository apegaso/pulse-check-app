/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PulseCheckAppTestModule } from '../../../test.module';
import { ParticipantPulseDetailComponent } from 'app/entities/participant-pulse/participant-pulse-detail.component';
import { ParticipantPulse } from 'app/shared/model/participant-pulse.model';

describe('Component Tests', () => {
    describe('ParticipantPulse Management Detail Component', () => {
        let comp: ParticipantPulseDetailComponent;
        let fixture: ComponentFixture<ParticipantPulseDetailComponent>;
        const route = ({ data: of({ participant: new ParticipantPulse(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PulseCheckAppTestModule],
                declarations: [ParticipantPulseDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ParticipantPulseDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ParticipantPulseDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.participant).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
