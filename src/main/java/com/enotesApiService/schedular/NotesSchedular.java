package com.enotesApiService.schedular;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.enotesApiService.entity.Notes;
import com.enotesApiService.repository.NotesRepository;

@Component // It is used to register a class as a bean in the spring container.Spring
			// automatically detetect it during
			// component scanning and create its object.

/*
 *  Scheduled job to permanently delete soft-deleted notes.
 *  This method runs every day at midnight and removes all notes
 * that have been marked as deleted for more than 7 days
 *  Prevents database from growing unnecessarily
 * - Allows temporary recovery before permanent deletion
 */

public class NotesSchedular {

	@Autowired
	private NotesRepository notesRepository;

//	@Scheduled(cron=" 0 0 0  * * ?")
	public void deleteNotesSchedular() {
		// 20 nov -14-n0v-7 days
		  // Calculate cutoff date (7 days before current time)
		LocalDateTime cutOffDate = LocalDateTime.now().minusDays(7);

		 // Fetch notes that are marked deleted and older than 7 days
		List<Notes> deleteNotes = notesRepository.findAllByIsDeletedAndDeletedOnBefore(true, cutOffDate);
		   // Permanently delete those notes from database
		notesRepository.deleteAll(deleteNotes);
	}
}
