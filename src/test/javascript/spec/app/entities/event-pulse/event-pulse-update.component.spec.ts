/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { PulseCheckAppTestModule } from '../../../test.module';
import { EventPulseUpdateComponent } from 'app/entities/event-pulse/event-pulse-update.component';
import { EventPulseService } from 'app/entities/event-pulse/event-pulse.service';
import { EventPulse } from 'app/shared/model/event-pulse.model';

describe('Component Tests', () => {
    describe('EventPulse Management Update Component', () => {
        let comp: EventPulseUpdateComponent;
        let fixture: ComponentFixture<EventPulseUpdateComponent>;
        let service: EventPulseService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PulseCheckAppTestModule],
                declarations: [EventPulseUpdateComponent]
            })
                .overrideTemplate(EventPulseUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(EventPulseUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EventPulseService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new EventPulse(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.event = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new EventPulse();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.event = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
