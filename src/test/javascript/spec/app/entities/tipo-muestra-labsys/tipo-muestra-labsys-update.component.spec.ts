/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { LabsysTestModule } from '../../../test.module';
import { TipoMuestraLabsysUpdateComponent } from 'app/entities/tipo-muestra-labsys/tipo-muestra-labsys-update.component';
import { TipoMuestraLabsysService } from 'app/entities/tipo-muestra-labsys/tipo-muestra-labsys.service';
import { TipoMuestraLabsys } from 'app/shared/model/tipo-muestra-labsys.model';

describe('Component Tests', () => {
    describe('TipoMuestraLabsys Management Update Component', () => {
        let comp: TipoMuestraLabsysUpdateComponent;
        let fixture: ComponentFixture<TipoMuestraLabsysUpdateComponent>;
        let service: TipoMuestraLabsysService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LabsysTestModule],
                declarations: [TipoMuestraLabsysUpdateComponent]
            })
                .overrideTemplate(TipoMuestraLabsysUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TipoMuestraLabsysUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TipoMuestraLabsysService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new TipoMuestraLabsys(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.tipoMuestra = entity;
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
                    const entity = new TipoMuestraLabsys();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.tipoMuestra = entity;
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
