package ru.daniil4jk.strongram.core.chain.caster;

import org.jetbrains.annotations.NotNull;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.api.objects.boost.ChatBoostAdded;
import org.telegram.telegrambots.meta.api.objects.boost.ChatBoostRemoved;
import org.telegram.telegrambots.meta.api.objects.boost.ChatBoostUpdated;
import org.telegram.telegrambots.meta.api.objects.business.BusinessConnection;
import org.telegram.telegrambots.meta.api.objects.business.BusinessMessagesDeleted;
import org.telegram.telegrambots.meta.api.objects.chat.Chat;
import org.telegram.telegrambots.meta.api.objects.chat.background.ChatBackground;
import org.telegram.telegrambots.meta.api.objects.chatmember.ChatMemberUpdated;
import org.telegram.telegrambots.meta.api.objects.forum.*;
import org.telegram.telegrambots.meta.api.objects.games.Animation;
import org.telegram.telegrambots.meta.api.objects.games.Game;
import org.telegram.telegrambots.meta.api.objects.gifts.GiftInfo;
import org.telegram.telegrambots.meta.api.objects.gifts.UniqueGiftInfo;
import org.telegram.telegrambots.meta.api.objects.giveaway.Giveaway;
import org.telegram.telegrambots.meta.api.objects.giveaway.GiveawayCompleted;
import org.telegram.telegrambots.meta.api.objects.giveaway.GiveawayCreated;
import org.telegram.telegrambots.meta.api.objects.giveaway.GiveawayWinners;
import org.telegram.telegrambots.meta.api.objects.inlinequery.ChosenInlineQuery;
import org.telegram.telegrambots.meta.api.objects.inlinequery.InlineQuery;
import org.telegram.telegrambots.meta.api.objects.location.Location;
import org.telegram.telegrambots.meta.api.objects.message.MaybeInaccessibleMessage;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.api.objects.message.PaidMessagePriceChanged;
import org.telegram.telegrambots.meta.api.objects.messageorigin.MessageOrigin;
import org.telegram.telegrambots.meta.api.objects.passport.PassportData;
import org.telegram.telegrambots.meta.api.objects.payments.*;
import org.telegram.telegrambots.meta.api.objects.payments.paidmedia.PaidMediaInfo;
import org.telegram.telegrambots.meta.api.objects.photo.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.polls.Poll;
import org.telegram.telegrambots.meta.api.objects.polls.PollAnswer;
import org.telegram.telegrambots.meta.api.objects.reactions.MessageReactionCountUpdated;
import org.telegram.telegrambots.meta.api.objects.reactions.MessageReactionUpdated;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.stories.Story;
import org.telegram.telegrambots.meta.api.objects.videochat.VideoChatEnded;
import org.telegram.telegrambots.meta.api.objects.videochat.VideoChatParticipantsInvited;
import org.telegram.telegrambots.meta.api.objects.videochat.VideoChatScheduled;
import org.telegram.telegrambots.meta.api.objects.videochat.VideoChatStarted;
import org.telegram.telegrambots.meta.api.objects.webapp.WebAppData;
import ru.daniil4jk.strongram.core.parser.to.text.TextromAnyParserService;

import java.util.List;

public class Casters {
    public static Caster<Update> asUpdate() {
        return update -> update;
    }

    public static Caster<String> asContent() {
        return update -> TextromAnyParserService.getInstance().parse(update);
    }

    public static @NotNull Caster<Message> asMessage() {
        return Update::getMessage;
    }

    public static @NotNull Caster<String> asText() {
        return asMessage().andThen(message -> {
            if (message.hasText()) return message.getText();
            if (message.hasCaption()) return message.getCaption();
            return null;
        });
    }

    public static @NotNull Caster<Integer> asDate() {
        return asMessage().andThen(Message::getDate);
    }

    public static @NotNull Caster<User> asForwardFrom() {
        return asMessage().andThen(Message::getForwardFrom);
    }

    public static @NotNull Caster<Chat> asForwardFromChat() {
        return asMessage().andThen(Message::getForwardFromChat);
    }

    public static @NotNull Caster<Integer> asForwardDate() {
        return asMessage().andThen(Message::getForwardDate);
    }

    public static @NotNull Caster<Contact> asContact() {
        return asMessage().andThen(Message::getContact);
    }

    public static @NotNull Caster<Location> asLocation() {
        return asMessage().andThen(Message::getLocation);
    }

    public static @NotNull Caster<Venue> asVenue() {
        return asMessage().andThen(Message::getVenue);
    }

    public static @NotNull Caster<Animation> asAnimation() {
        return asMessage().andThen(Message::getAnimation);
    }

    public static @NotNull Caster<MaybeInaccessibleMessage> asPinnedMessage() {
        return asMessage().andThen(Message::getPinnedMessage);
    }

    public static @NotNull Caster<User> asLeftChatMember() {
        return asMessage().andThen(Message::getLeftChatMember);
    }

    public static @NotNull Caster<String> asNewChatTitle() {
        return asMessage().andThen(Message::getNewChatTitle);
    }

    public static @NotNull Caster<List<PhotoSize>> asNewChatPhoto() {
        return asMessage().andThen(Message::getNewChatPhoto);
    }

    public static @NotNull Caster<Boolean> asDeleteChatPhoto() {
        return asMessage().andThen(Message::getDeleteChatPhoto);
    }

    public static @NotNull Caster<Boolean> asGroupchatCreated() {
        return asMessage().andThen(Message::getGroupchatCreated);
    }

    public static @NotNull Caster<Message> asReplyToMessage() {
        return asMessage().andThen(Message::getReplyToMessage);
    }

    public static @NotNull Caster<Voice> asVoice() {
        return asMessage().andThen(Message::getVoice);
    }

    public static @NotNull Caster<String> asCaption() {
        return asMessage().andThen(Message::getCaption);
    }

    public static @NotNull Caster<Boolean> asSuperGroupCreated() {
        return asMessage().andThen(Message::getSuperGroupCreated);
    }

    public static @NotNull Caster<Boolean> asChannelChatCreated() {
        return asMessage().andThen(Message::getChannelChatCreated);
    }

    public static @NotNull Caster<Long> asMigrateToChatId() {
        return asMessage().andThen(Message::getMigrateToChatId);
    }

    public static @NotNull Caster<Long> asMigrateFromChatId() {
        return asMessage().andThen(Message::getMigrateFromChatId);
    }

    public static @NotNull Caster<Integer> asEditDate() {
        return asMessage().andThen(Message::getEditDate);
    }

    public static @NotNull Caster<Game> asGame() {
        return asMessage().andThen(Message::getGame);
    }

    public static @NotNull Caster<Integer> asForwardFromMessageId() {
        return asMessage().andThen(Message::getForwardFromMessageId);
    }

    public static @NotNull Caster<Invoice> asInvoice() {
        return asMessage().andThen(Message::getInvoice);
    }

    public static @NotNull Caster<SuccessfulPayment> asSuccessfulPayment() {
        return asMessage().andThen(Message::getSuccessfulPayment);
    }

    public static @NotNull Caster<VideoNote> asVideoNote() {
        return asMessage().andThen(Message::getVideoNote);
    }

    public static @NotNull Caster<String> asAuthorSignature() {
        return asMessage().andThen(Message::getAuthorSignature);
    }

    public static @NotNull Caster<String> asForwardSignature() {
        return asMessage().andThen(Message::getForwardSignature);
    }

    public static @NotNull Caster<String> asMediaGroupId() {
        return asMessage().andThen(Message::getMediaGroupId);
    }

    public static @NotNull Caster<String> asConnectedWebsite() {
        return asMessage().andThen(Message::getConnectedWebsite);
    }

    public static @NotNull Caster<PassportData> asPassportData() {
        return asMessage().andThen(Message::getPassportData);
    }

    public static @NotNull Caster<String> asForwardSenderName() {
        return asMessage().andThen(Message::getForwardSenderName);
    }

    public static @NotNull Caster<Poll> asMessagePoll() {
        return asMessage().andThen(Message::getPoll);
    }

    public static @NotNull Caster<InlineKeyboardMarkup> asReplyMarkup() {
        return asMessage().andThen(Message::getReplyMarkup);
    }

    public static @NotNull Caster<Dice> asDice() {
        return asMessage().andThen(Message::getDice);
    }

    public static @NotNull Caster<User> asViaBot() {
        return asMessage().andThen(Message::getViaBot);
    }

    public static @NotNull Caster<Chat> asSenderChat() {
        return asMessage().andThen(Message::getSenderChat);
    }

    public static @NotNull Caster<ProximityAlertTriggered> asProximityAlertTriggered() {
        return asMessage().andThen(Message::getProximityAlertTriggered);
    }

    public static @NotNull Caster<MessageAutoDeleteTimerChanged> asMessageAutoDeleteTimerChanged() {
        return asMessage().andThen(Message::getMessageAutoDeleteTimerChanged);
    }

    public static @NotNull Caster<Boolean> asIsAutomaticForward() {
        return asMessage().andThen(Message::getIsAutomaticForward);
    }

    public static @NotNull Caster<Boolean> asHasProtectedContent() {
        return asMessage().andThen(Message::getHasProtectedContent);
    }

    public static @NotNull Caster<WebAppData> asWebAppData() {
        return asMessage().andThen(Message::getWebAppData);
    }

    public static @NotNull Caster<VideoChatStarted> asVideoChatStarted() {
        return asMessage().andThen(Message::getVideoChatStarted);
    }

    public static @NotNull Caster<VideoChatEnded> asVideoChatEnded() {
        return asMessage().andThen(Message::getVideoChatEnded);
    }

    public static @NotNull Caster<VideoChatParticipantsInvited> asVideoChatParticipantsInvited() {
        return asMessage().andThen(Message::getVideoChatParticipantsInvited);
    }

    public static @NotNull Caster<VideoChatScheduled> asVideoChatScheduled() {
        return asMessage().andThen(Message::getVideoChatScheduled);
    }

    public static @NotNull Caster<Boolean> asIsTopicMessage() {
        return asMessage().andThen(Message::getIsTopicMessage);
    }

    public static @NotNull Caster<ForumTopicCreated> asForumTopicCreated() {
        return asMessage().andThen(Message::getForumTopicCreated);
    }

    public static @NotNull Caster<ForumTopicClosed> asForumTopicClosed() {
        return asMessage().andThen(Message::getForumTopicClosed);
    }

    public static @NotNull Caster<ForumTopicReopened> asForumTopicReopened() {
        return asMessage().andThen(Message::getForumTopicReopened);
    }

    public static @NotNull Caster<ForumTopicEdited> asForumTopicEdited() {
        return asMessage().andThen(Message::getForumTopicEdited);
    }

    public static @NotNull Caster<GeneralForumTopicHidden> asGeneralForumTopicHidden() {
        return asMessage().andThen(Message::getGeneralForumTopicHidden);
    }

    public static @NotNull Caster<GeneralForumTopicUnhidden> asGeneralForumTopicUnhidden() {
        return asMessage().andThen(Message::getGeneralForumTopicUnhidden);
    }

    public static @NotNull Caster<WriteAccessAllowed> asWriteAccessAllowed() {
        return asMessage().andThen(Message::getWriteAccessAllowed);
    }

    public static @NotNull Caster<Boolean> asHasMediaSpoiler() {
        return asMessage().andThen(Message::getHasMediaSpoiler);
    }

    public static @NotNull Caster<UserShared> asUserShared() {
        return asMessage().andThen(Message::getUserShared);
    }

    public static @NotNull Caster<ChatShared> asChatShared() {
        return asMessage().andThen(Message::getChatShared);
    }

    public static @NotNull Caster<Story> asStory() {
        return asMessage().andThen(Message::getStory);
    }

    public static @NotNull Caster<ExternalReplyInfo> asExternalReplyInfo() {
        return asMessage().andThen(Message::getExternalReplyInfo);
    }

    public static @NotNull Caster<MessageOrigin> asForwardOrigin() {
        return asMessage().andThen(Message::getForwardOrigin);
    }

    public static @NotNull Caster<LinkPreviewOptions> asLinkPreviewOptions() {
        return asMessage().andThen(Message::getLinkPreviewOptions);
    }

    public static @NotNull Caster<TextQuote> asQuote() {
        return asMessage().andThen(Message::getQuote);
    }

    public static @NotNull Caster<UsersShared> asUsersShared() {
        return asMessage().andThen(Message::getUsersShared);
    }

    public static @NotNull Caster<GiveawayCreated> asGiveawayCreated() {
        return asMessage().andThen(Message::getGiveawayCreated);
    }

    public static @NotNull Caster<Giveaway> asGiveaway() {
        return asMessage().andThen(Message::getGiveaway);
    }

    public static @NotNull Caster<GiveawayWinners> asGiveawayWinners() {
        return asMessage().andThen(Message::getGiveawayWinners);
    }

    public static @NotNull Caster<GiveawayCompleted> asGiveawayCompleted() {
        return asMessage().andThen(Message::getGiveawayCompleted);
    }

    public static @NotNull Caster<Story> asReplyToStory() {
        return asMessage().andThen(Message::getReplyToStory);
    }

    public static @NotNull Caster<ChatBoostAdded> asBoostAdded() {
        return asMessage().andThen(Message::getBoostAdded);
    }

    public static @NotNull Caster<Integer> asSenderBoostCount() {
        return asMessage().andThen(Message::getSenderBoostCount);
    }

    public static @NotNull Caster<String> asBusinessConnectionId() {
        return asMessage().andThen(Message::getBusinessConnectionId);
    }

    public static @NotNull Caster<User> asSenderBusinessBot() {
        return asMessage().andThen(Message::getSenderBusinessBot);
    }

    public static @NotNull Caster<Boolean> asIsFromOffline() {
        return asMessage().andThen(Message::getIsFromOffline);
    }

    public static @NotNull Caster<ChatBackground> asChatBackgroundSet() {
        return asMessage().andThen(Message::getChatBackgroundSet);
    }

    public static @NotNull Caster<String> asEffectId() {
        return asMessage().andThen(Message::getEffectId);
    }

    public static @NotNull Caster<Boolean> asShowCaptionAboveMedia() {
        return asMessage().andThen(Message::getShowCaptionAboveMedia);
    }

    public static @NotNull Caster<PaidMediaInfo> asPaidMedia() {
        return asMessage().andThen(Message::getPaidMedia);
    }

    public static @NotNull Caster<RefundedPayment> asRefundedPayment() {
        return asMessage().andThen(Message::getRefundedPayment);
    }

    public static @NotNull Caster<GiftInfo> asGift() {
        return asMessage().andThen(Message::getGift);
    }

    public static @NotNull Caster<UniqueGiftInfo> asUniqueGift() {
        return asMessage().andThen(Message::getUniqueGift);
    }

    public static @NotNull Caster<PaidMessagePriceChanged> asPaidMessagePriceChanged() {
        return asMessage().andThen(Message::getPaidMessagePriceChanged);
    }

    public static @NotNull Caster<Integer> asPaidStarCount() {
        return asMessage().andThen(Message::getPaidStarCount);
    }

    public static @NotNull Caster<InlineQuery> asInlineQuery() {
        return Update::getInlineQuery;
    }

    public static @NotNull Caster<ChosenInlineQuery> asChosenInlineQuery() {
        return Update::getChosenInlineQuery;
    }

    public static @NotNull Caster<CallbackQuery> asCallbackQuery() {
        return Update::getCallbackQuery;
    }

    public static @NotNull Caster<Message> asEditedMessage() {
        return Update::getEditedMessage;
    }

    public static @NotNull Caster<Message> asChannelPost() {
        return Update::getChannelPost;
    }

    public static @NotNull Caster<Message> asEditedChannelPost() {
        return Update::getEditedChannelPost;
    }

    public static @NotNull Caster<ShippingQuery> asShippingQuery() {
        return Update::getShippingQuery;
    }

    public static @NotNull Caster<PreCheckoutQuery> asPreCheckoutQuery() {
        return Update::getPreCheckoutQuery;
    }

    public static @NotNull Caster<Poll> asPoll() {
        return Update::getPoll;
    }

    public static @NotNull Caster<PollAnswer> asPollAnswer() {
        return Update::getPollAnswer;
    }

    public static @NotNull Caster<ChatMemberUpdated> asMyChatMember() {
        return Update::getMyChatMember;
    }

    public static @NotNull Caster<ChatMemberUpdated> asChatMember() {
        return Update::getChatMember;
    }

    public static @NotNull Caster<ChatJoinRequest> asChatJoinRequest() {
        return Update::getChatJoinRequest;
    }

    public static @NotNull Caster<MessageReactionUpdated> asMessageReaction() {
        return Update::getMessageReaction;
    }

    public static @NotNull Caster<MessageReactionCountUpdated> asMessageReactionCount() {
        return Update::getMessageReactionCount;
    }

    public static @NotNull Caster<ChatBoostUpdated> asChatBoost() {
        return Update::getChatBoost;
    }

    public static @NotNull Caster<ChatBoostRemoved> asRemovedChatBoost() {
        return Update::getRemovedChatBoost;
    }

    public static @NotNull Caster<BusinessConnection> asBusinessConnection() {
        return Update::getBusinessConnection;
    }

    public static @NotNull Caster<Message> asBusinessMessage() {
        return Update::getBusinessMessage;
    }

    public static @NotNull Caster<Message> asEditedBusinessMessage() {
        return Update::getEditedBuinessMessage;
    }

    public static @NotNull Caster<BusinessMessagesDeleted> asDeletedBusinessMessages() {
        return Update::getDeletedBusinessMessages;
    }

    public static @NotNull Caster<PaidMediaPurchased> asPaidMediaPurchased() {
        return Update::getPaidMediaPurchased;
    }
}
