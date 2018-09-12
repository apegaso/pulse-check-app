/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PulseCheckAppTestModule } from '../../../test.module';
import { QuestionPulseDeleteDialogComponent } from 'app/entities/question-pulse/question-pulse-delete-dialog.component';
import { QuestionPulseService } from 'app/entities/question-pulse/question-pulse.service';

describe('Component Tests', () => {
    describe('QuestionPulse Management Delete Component', () => {
        let comp: QuestionPulseDeleteDialogComponent;
        let fixture: ComponentFixture<QuestionPulseDeleteDialogComponent>;
        let service: QuestionPulseService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PulseCheckAppTestModule],
                declarations: [QuestionPulseDeleteDialogComponent]
            })
                .overrideTemplate(QuestionPulseDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(QuestionPulseDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(QuestionPulseService);
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
