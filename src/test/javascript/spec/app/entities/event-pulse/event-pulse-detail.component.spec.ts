/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PulseCheckAppTestModule } from '../../../test.module';
import { EventPulseDetailComponent } from 'app/entities/event-pulse/event-pulse-detail.component';
import { EventPulse } from 'app/shared/model/event-pulse.model';

describe('Component Tests', () => {
    describe('EventPulse Management Detail Component', () => {
        let comp: EventPulseDetailComponent;
        let fixture: ComponentFixture<EventPulseDetailComponent>;
        const route = ({ data: of({ event: new EventPulse(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PulseCheckAppTestModule],
                declarations: [EventPulseDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(EventPulseDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(EventPulseDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.event).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
