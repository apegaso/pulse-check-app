/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PulseCheckAppTestModule } from '../../../test.module';
import { QuestionGroupPulseDeleteDialogComponent } from 'app/entities/question-group-pulse/question-group-pulse-delete-dialog.component';
import { QuestionGroupPulseService } from 'app/entities/question-group-pulse/question-group-pulse.service';

describe('Component Tests', () => {
    describe('QuestionGroupPulse Management Delete Component', () => {
        let comp: QuestionGroupPulseDeleteDialogComponent;
        let fixture: ComponentFixture<QuestionGroupPulseDeleteDialogComponent>;
        let service: QuestionGroupPulseService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PulseCheckAppTestModule],
                declarations: [QuestionGroupPulseDeleteDialogComponent]
            })
                .overrideTemplate(QuestionGroupPulseDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(QuestionGroupPulseDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(QuestionGroupPulseService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it(
                'Should call delete service on confirmDelete',
                inject(
                    [],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });
});
